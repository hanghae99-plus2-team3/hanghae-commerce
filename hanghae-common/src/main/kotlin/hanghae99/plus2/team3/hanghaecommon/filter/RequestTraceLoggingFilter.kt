package hanghae99.plus2.team3.hanghaecommon.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


/**
 * RequestTraceLoggingFilter
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/30
 */

class RequestTraceLoggingFilter(
    private val log: Logger,
    private val domain: String
) : OncePerRequestFilter() {

    companion object {
        const val REQUEST_TRACE_ID = "REQUEST_TRACE_ID"
        const val REQUEST_TIME = "REQUEST_TIME"
        const val PROCESSING_DOMAIN = "PROCESSING_DOMAIN"
        const val ELAPSED_TIME_THRESHOLD = 4000L
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val traceId = request.getHeader(REQUEST_TRACE_ID) ?: generateTraceId()
        MDC.put(REQUEST_TRACE_ID, traceId)
        MDC.put(REQUEST_TIME, System.currentTimeMillis().toString())
        MDC.put(PROCESSING_DOMAIN, domain)

        filterChain.doFilter(requestLog(request), response)

        val elapsedTime = if (MDC.get(REQUEST_TIME) != null) {
            System.currentTimeMillis() - (MDC.get(REQUEST_TIME)).toLong()
        } else 0

        if (elapsedTime > ELAPSED_TIME_THRESHOLD) {
            log.error("SLOW RESPONSE @@@@@@@==>  [REQUEST_TIME] : {} ms", elapsedTime)
        } else if (elapsedTime > 0) {
            log.info("Request Ended ==>  [REQUEST_TIME] : {} ms", elapsedTime)
        }


        MDC.clear()
    }

    private fun requestLog(request: HttpServletRequest): HttpServletRequest {


        log.info(
            "Request Started ===> [ {} ] : {} ",
            request.method,
            request.requestURI
        )

        if (request.queryString != null)
            log.info("Request QueryString ===> [ {} ]", request.queryString)
        val contentType = request.contentType
        if (!contentType.lowercase(Locale.getDefault()).startsWith("multipart/form-data")) {
            val requestBody: String = readBody(request)
            if (requestBody.isNotEmpty()) {
                log.info("request body ===> [ {} ]", requestBody)
                return BufferedRequestWrapper(request, requestBody)
            }
        }
        return request
    }

    private fun generateTraceId() = UUID.randomUUID().toString().substring(0, 8)

    private fun readBody(request: HttpServletRequest): String {
        val stringBuilder = StringBuilder()
        var bufferedReader: BufferedReader? = null
        try {
            request.inputStream.use { inputStream ->
                if (inputStream != null) {
                    bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val charBuffer = CharArray(128)
                    var bytesRead: Int
                    while (bufferedReader!!.read(charBuffer).also { bytesRead = it } > 0) {
                        stringBuilder.appendRange(charBuffer, 0, bytesRead)
                    }
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader!!.close()
            }
        }
        return stringBuilder.toString()
    }

}

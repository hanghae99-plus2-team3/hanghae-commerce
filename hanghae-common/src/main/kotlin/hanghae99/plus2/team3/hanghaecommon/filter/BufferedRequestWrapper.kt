package hanghae99.plus2.team3.hanghaecommon.filter

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

/**
 * BufferedRequestWrapper
 *
 * @author jaypark
 * @version 1.0.0
 * @since 10/17/23
 */
class BufferedRequestWrapper(
    request: HttpServletRequest,
    private val body: String,
    private val inputStream: ByteArrayInputStream = ByteArrayInputStream(body.toByteArray())
) : HttpServletRequestWrapper(request) {


    override fun getInputStream(): ServletInputStream {
        return object : ServletInputStream() {
            override fun read() = inputStream.read()

            override fun isFinished() = inputStream.available() == 0

            override fun isReady() = true

            override fun setReadListener(listener: ReadListener?) {}
        }
    }

    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream))
    }
}

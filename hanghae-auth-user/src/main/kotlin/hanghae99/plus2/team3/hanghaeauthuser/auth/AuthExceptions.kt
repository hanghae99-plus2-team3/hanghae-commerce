package hanghae99.plus2.team3.hanghaeauthuser.auth

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class UnAuthorizedException : RuntimeException()

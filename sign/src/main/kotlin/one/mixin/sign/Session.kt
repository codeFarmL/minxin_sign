package one.mixin.sign

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import okhttp3.Request
import okio.ByteString.Companion.encode
import one.mixin.sign.extension.base64Decode
import one.mixin.sign.extension.base64Encode
import one.mixin.sign.extension.bodyToString
import one.mixin.sign.extension.path
import one.mixin.sign.extension.toLeByteArray
import one.mixin.sign.util.aesEncrypt
import java.security.Key
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

fun signToken(userId: String, sessionId: String, method: String, path: String, body: String?, key: Key): String {
    val expire = System.currentTimeMillis() / 1000 + 1800
    val iat = System.currentTimeMillis() / 1000

    var content = "${method}$path"
    body?.let {
        content += body
    }
    return Jwts.builder()
        .setClaims(
            ConcurrentHashMap<String, Any>().apply {
                put(Claims.ID, UUID.randomUUID().toString())
                put(Claims.EXPIRATION, expire)
                put(Claims.ISSUED_AT, iat)
                put("uid", userId)
                put("sid", sessionId)
                put("sig", content.encode().sha256().hex())
                put("scp", "FULL")
            }
        )
        .signWith(key)
        .compact()
}

fun signToken(userId: String, sessionId: String, request: Request, key: Key): String {
    var body: String? = null
    request.body?.apply {
        if (contentLength() > 0) {
            body = bodyToString()
        }
    }
    return signToken(userId, sessionId, request.method, request.url.path(), body, key)
}

@JvmOverloads
fun encryptPin(key: String, pin: String, iterator: Long = System.currentTimeMillis() * 1_000_000): String {
    val pinByte = pin.toByteArray() + (System.currentTimeMillis() / 1000).toLeByteArray() + iterator.toLeByteArray()
    return aesEncrypt(key.base64Decode(), pinByte).base64Encode()
}

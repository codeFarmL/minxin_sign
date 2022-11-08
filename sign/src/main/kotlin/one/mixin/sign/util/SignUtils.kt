package one.mixin.sign.util

import net.i2p.crypto.eddsa.EdDSAPrivateKey
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec
import one.mixin.sign.extension.base64Decode
import one.mixin.sign.extension.base64Encode
import one.mixin.sign.signToken
import java.util.*

class SignUtils {
    companion object{
        /**
         * 获取EdDSA的Authorization信息
         *
         * @param userId
         * @param sessionId
         * @param method
         * @param path
         * @param bodyToString
         * @param privateKey
         * @return
         */
        @JvmStatic
        fun getEdDSASignToken(
            userId: String, sessionId: String, method: String, path: String, bodyToString: String?, privateKey: String
        ): String {
            val seed = getEdDSAPrivateKeyFromString(privateKey).seed.base64Encode()
            val privateSpec = EdDSAPrivateKeySpec(
                seed.base64Decode(),
                ed25519
            )
            return signToken(
                userId,
                sessionId,
                method,
                path,
                bodyToString,
                EdDSAPrivateKey(privateSpec)
            )
        }

        /**
         * 获取pinToken
         *
         *
         *
         * @param privateKey 私钥
         * @param pinTokenPem pinTokenPem
         * @return
         */
        @JvmStatic
        fun getPinToken(privateKey: String?, pinTokenPem: String?): String? {
            val key = getEdDSAPrivateKeyFromString(privateKey!!)
            return decryASEKey(pinTokenPem!!, key)
        }

    }

}
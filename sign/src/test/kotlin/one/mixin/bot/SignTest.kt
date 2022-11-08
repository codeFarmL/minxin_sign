package one.mixin.bot

import one.mixin.sign.util.SignUtils.Companion.getEdDSASignToken
import one.mixin.sign.util.SignUtils.Companion.getPinToken
import kotlin.test.Test


class SignTest {
    @Test fun testPinToken() {
        println(getPinToken("ZywWUtZxMUxdS6LHSEqVsxFr9Y3pM5OJDx648Hm54Tgn9IoGhUOLbNoQ2nKewVNnCsBF3qOcb4nuiowuxQax4w","kMu0ihumvCC0t1sgbD1n1sv8TQLFVje4qWv-Wpy_Ihg"))
    }

    @Test fun testSignToken() {
        val authorization = getEdDSASignToken(
            "24888245-d200-4907-aff0-1303d94217d5",
            "0d89aa10-f559-400e-9323-9d103f9fd49e",
            "POST",
            "/transfer",
            null,
            "ZywWUtZxMUxdS6LHSEqVsxFr9Y3pM5OJDx648Hm54Tgn9IoGhUOLbNoQ2nKewVNnCsBF3qOcb4nuiowuxQax4w"
        )
        println(authorization)
    }
}

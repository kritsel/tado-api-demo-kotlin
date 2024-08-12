package tadoclient.apis.verify

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.test.assertNotNull

// results in : org.opentest4j.AssertionFailedError: property Home.partner is null ==> expected: not <null>
fun verifyAny(anObject:Any, context:String, parentName:String, nullAllowedProperties:List<String> = emptyList()) {
    anObject::class.memberProperties.forEach {
        if (it.name !in nullAllowedProperties) {
            assertNotNull((it as KProperty1<Any, *>).get(anObject), "[$context] property ${parentName}.${it.name} is null")
        }
    }
}
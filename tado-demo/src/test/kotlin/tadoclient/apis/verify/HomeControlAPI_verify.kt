package tadoclient.apis.verify

import tadoclient.models.*
import kotlin.test.assertNotNull



fun verifyHomeState(homeState: HomeState, context:String, parentName:String = "HomeState") {
    verifyAny(homeState, context, parentName)
}


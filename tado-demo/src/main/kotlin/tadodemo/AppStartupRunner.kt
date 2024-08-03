package tadodemo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component


@Component
class AppStartupRunner(private val tadoApiDemo: TadoApiDemo) : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        tadoApiDemo.doIt()
    }
}
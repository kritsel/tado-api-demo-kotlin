package tadodemo

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class AppStartupRunner() : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        // print the legend for the information collected in ScheduledTask.collectTadoMetrics
        System.out.println("*************************************")
        System.out.println()
        System.out.println("Sit back, relax, and watch some of your homes datapoints being printed every minute.")
        System.out.println()
        System.out.println("*************************************")
        System.out.println("Legend:")
        System.out.println("- HP = heating power")
        System.out.println("- T  = inside temperature")
        System.out.println("- H  = inside humidity")
        System.out.println("*************************************")
        System.out.println()
    }
}
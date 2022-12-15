package config

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object ExecutorsConfig {
  val defaultExecutor = Executors.newFixedThreadPool(10)
  implicit val defaultExecutionCtx: ExecutionContext = ExecutionContext.fromExecutorService(ExecutorsConfig.defaultExecutor)
}

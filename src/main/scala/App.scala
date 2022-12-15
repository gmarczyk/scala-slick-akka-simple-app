import adapters.api.books.BookEndpoints
import akka.http.scaladsl.Http

import scala.io.StdIn

object App {

  import config.ApiConfig.system
  import config.ExecutorsConfig.defaultExecutionCtx

  def main(args: Array[String]): Unit = {
    val port = 8080
    val host = "0.0.0.0"
    val binding = Http().newServerAt(host, port).bind(BookEndpoints.route)

    println(s"Server online at $host:$port - Press RETURN to stop...")
    StdIn.readLine()
    binding.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}

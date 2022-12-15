package adapters.api.books

import adapters.api.books.request.{AddBookRequest, AddCommentRequest, DeleteBookRequest, UpdateBookRequest}
import adapters.api.books.response.ListBooksResponseBuilder
import adapters.db.sql.SqlDbBookRepository
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, complete, concat, delete, entity, get, onComplete, path, pathPrefix, post, put}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpjackson.JacksonSupport
import domain._

import scala.concurrent.Future
import scala.util.{Failure, Success}

object BookEndpoints extends JacksonSupport {
  private val bookRepository = new SqlDbBookRepository()

  private val addBookUseCase = new AddBookUseCase(bookRepository)
  private val updateBookUseCase = new UpdateBookUseCase(bookRepository)
  private val deleteBookUseCase = new DeleteBookUseCase(bookRepository)
  private val listBooksUseCase = new ListBooksUseCase(bookRepository)
  private val addCommentUseCase = new AddCommentUseCase(bookRepository)

  val route: Route = {
    pathPrefix("books") {
      concat(
        post {
          entity(as[AddBookRequest]) {
            request => {
              handleResult(addBookUseCase.addBook(request.title, request.author, request.isbn, request.rating))
            }
          }
        },
        put {
          entity(as[UpdateBookRequest]) {
            request => {
              handleResult(updateBookUseCase.updateBook(request.id, request.title, request.author, request.isbn, request.rating))
            }
          }
        },
        delete {
          entity(as[DeleteBookRequest]) {
            request => {
              handleResult(deleteBookUseCase.deleteBook(request.id))
            }
          }
        },
        get {
          onComplete(listBooksUseCase.listBooks()) {
            case Success(books) => complete(StatusCodes.OK, ListBooksResponseBuilder.from(books))
            case Failure(_) => complete(StatusCodes.InternalServerError)
          }
        },
        path("comments") {
          post {
            entity(as[AddCommentRequest]) { request =>
              handleResult(addCommentUseCase.addComment(request.bookId, request.comment))
            }
          }
        }
      )
    }
  }

  def handleResult(result: Either[String, Future[Int]]) = {
    result match {
      case Left(error) => complete(StatusCodes.BadRequest, error)
      case Right(future) => onComplete(future) {
        case Success(_) => complete(StatusCodes.OK)
        case Failure(_) => complete(StatusCodes.InternalServerError)
      }
    }
  }
}



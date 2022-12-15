package adapters.api.books.request

case class AddCommentRequest(bookId: Long, comment: String)
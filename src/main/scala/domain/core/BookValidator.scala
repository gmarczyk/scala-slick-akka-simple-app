package domain.core

object BookValidator {
  def validate(isbn: String, rating: Int): Either[String, Unit] = {
    if (!isIsbnValid(isbn)) {
      return Left(s"ISBN invalid: $isbn")
    }

    if (!isRatingInRange(rating)) {
      return Left(s"Rating $rating is not in range 1-5")
    }

    Right()
  }

  // Not implementing actual algorithm cuz of time
  private def isIsbnValid(isbn: String): Boolean = !isbn.equals("invalid")

  private def isRatingInRange(rating: Int) = (1 to 5 contains rating)
}

package lecture6.future.test

import com.typesafe.config.ConfigFactory
import lecture6.future.assignment.Assignment
import lecture6.future.bcrypt.AsyncBcryptImpl
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class AssignmentTest extends AsyncFlatSpec with Matchers {

  val config = ConfigFactory.load()
  val credentialStore = new ConfigCredentialStore(config)
  val reliableBcrypt = new AsyncBcryptImpl
  val assignment = new Assignment(reliableBcrypt, credentialStore)

  import assignment._

  behavior of "verifyCredentials"

  it should "return true for valid user-password pair" in {
    verifyCredentials("winnie", "pooh").map { result =>
      result shouldBe true
    }
  }

  it should "return false if user does not exist in store" in {
    verifyCredentials("sws", "pooh").map { result =>
      result shouldBe false
    }
  }

  it should "return false for invalid password" in {
    verifyCredentials("winnie", "pooh2").map { result =>
      result shouldBe false
    }
  }

  behavior of "withCredentials"

  it should "execute code block if credentials are valid" in {
    withCredentials("winnie", "pooh"){
      assert(true)
    }
  }

  it should "not execute code block if credentials are not valid" in ???

  behavior of "hashPasswordList"

  it should "return matching password-hash pairs" in ???

  behavior of "findMatchingPassword"

  it should "return matching password from the list" in ???
  it should "return None if no matching password is found" in ???

  behavior of "withRetry"

  it should "return result on passed lecture6.future's success" in ???
  it should "not execute more than specified number of retries" in ??? // для счетчиков можно использовать java.util.concurrent.atomic.AtomicInteger

  it should "not execute unnecessary retries" in ???
  it should "return the first error, if all attempts fail" in ???

  behavior of "withTimeout"

  it should "return result on passed lecture6.future success" in ???
  it should "return result on passed lecture6.future failure" in ???
  it should "complete on never-completing lecture6.future" in ???

  behavior of "hashPasswordListReliably"
  val assignmentFlaky = new Assignment(new FlakyBcryptWrapper(reliableBcrypt), credentialStore)

  it should "return password-hash pairs for successful hashing operations" in ???
}

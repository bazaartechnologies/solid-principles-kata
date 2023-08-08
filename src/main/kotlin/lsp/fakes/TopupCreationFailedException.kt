package lsp.fakes

import java.lang.RuntimeException

class TopupCreationFailedException(userId: String?, ex: Throwable?) :
    RuntimeException(String.format("Failed to create Topup for user [%s]", userId), ex)
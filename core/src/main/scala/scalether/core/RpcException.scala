package scalether.core

import scalether.domain.Error

class RpcException(val error: Error) extends Exception(s"${error.code}: ${error.message}")
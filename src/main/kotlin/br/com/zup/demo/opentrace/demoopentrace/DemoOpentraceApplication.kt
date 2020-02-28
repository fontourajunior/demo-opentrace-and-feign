package br.com.zup.demo.opentrace.demoopentrace

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler


@SpringBootApplication
@EnableFeignClients
class DemoOpentraceApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<DemoOpentraceApplication>(*args)
        }
    }

    @Bean
    fun errorDecoder(): FeignErrorDecoder =
            FeignErrorDecoder()

}


@RestController
@RequestMapping("/address")
class AddressController(private val addressService: AddressServiceApi) {

    @PostMapping("/{zipCode}")
    @ResponseBody
    fun findAddress(@PathVariable("zipCode") zipCode: String): Map<String, Any> =
            addressService.findAddressByZipCode(zipCode)

}

/**
 * Feign
 */
@FeignClient("addressService", url = "http://viacep.com.br")
interface AddressServiceApi {

    @GetMapping("/ws/{zipCode}/json/", consumes = ["application/json"])
    @ResponseBody
    fun findAddressByZipCode(@PathVariable("zipCode") zipCode: String): Map<String, Any>

}

class FeignErrorDecoder : ErrorDecoder {

    override fun decode(key: String, response: Response): Exception =
            when (response.status()) {
                400 -> BusinessException("Deu ruim")
                else -> Exception(response.reason())
            }

}

/**
 * Exception Handler
 */
@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun handleBusinessExceptions(ex: BusinessException) = MessageError(ex.errorCode)

}

data class MessageError(val message: String)

class BusinessException(val errorCode: String) : RuntimeException()

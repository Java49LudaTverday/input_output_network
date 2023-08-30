package telran.net.calculatorTCP;

import telran.net.*;

public class CalculatorProtocol implements ApplProtocol {

	@Override
	public Response getResponse(Request request) {
		// <operator> -> operators: + ; - ; / ; * -> type request
		// double[]{op1, op2} -> <firstOperand><secondOperand> -> requesData
		// operators : any double number
		
		String requestType = request.requestType();
		Double[] operands = (Double[]) request.requestData();
		
		Response response = switch (requestType) {
		         case "add" -> new Response(ResponseCode.OK, operands[0] + operands[1] );
		         case "minus" -> new Response(ResponseCode.OK, operands[0] - operands[1]);
		         case "multiply" -> new Response(ResponseCode.OK, operands[0] * operands[1]);
		         case "divide" -> new Response(ResponseCode.OK, operands[0] / operands[1]);
		         default -> new Response(ResponseCode.WRONG_TYPE, "wrong request type");
		         };
		   return response;
	}

}

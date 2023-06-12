import org.eclipse.uml2.uml.Parameter;

public class Methode {
	 public static String umlMethodeToPumlMethode(org.eclipse.uml2.uml.Operation operation) {
	     StringBuilder ret = new StringBuilder();

	     if (operation.isStatic()) {
	         ret.append("{static} ");
	     }

	     ret.append(getAbstractSymbol(operation)).append(Class.getVisibilitySymbol(operation.getVisibility()))
	             .append(operation.getName()).append(getParametersString(operation));
	     ret.append(": ").append(getReturnType(operation));


	     return ret.toString();
	 }
	 
	 private static String getAbstractSymbol(org.eclipse.uml2.uml.Operation operation) {
	     if (operation.isAbstract()) {
	         return "{abstract} ";
	     } else {
	         return "";
	     }
	 }
	 
	 private static String getParametersString(org.eclipse.uml2.uml.Operation operation) {
	     StringBuilder parameters = new StringBuilder();

	     parameters.append("(");

	     for (Parameter parameter : operation.getOwnedParameters()) {
	    	 if (parameter.getName() == null) {
	    		 break;
	    	 }
	         parameters.append(parameter.getName()).append(":")
	                 .append(parameter.getType().getName()).append(", ");
	     }

	     if (parameters.length() > 1) {
	         parameters.setLength(parameters.length() - 2); // Remove trailing comma and space
	     }

	     parameters.append(")");

	     return parameters.toString();
	 }
	 
	 private static String getReturnType(org.eclipse.uml2.uml.Operation operation) {
	     if (operation.getType() != null) {
	         return operation.getType().getName();
	     } else {
	         return "";
	     }
	 }
}

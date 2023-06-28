import org.eclipse.uml2.uml.Parameter;

/*
 *  The 'Methode' class contains static methods to convert UML operation objects into PlantUML method objects.
 */
public class Operation {

	/*
	 * Converts a UML operation object into a PlantUML method object in puml format.
	 * 
	 *@param operation The UML operation object to be converted. 
	 *@return The converted PlantUML method object as a string in puml format.
	 */
    public static String umlOperationToPumlOperation(org.eclipse.uml2.uml.Operation operation) {
        StringBuilder ret = new StringBuilder();

        if (operation.isStatic()) {
            ret.append("{static} ");
        }

        ret.append(getAbstractSymbol(operation))
                .append(Class.getVisibilitySymbol(operation.getVisibility()))
                .append(operation.getName())
                .append(getParametersString(operation));

        ret.append(": ").append(getReturnType(operation));

        return ret.toString();
    }


    /*
     * Retrieves the abstract symbol for the given UML operation.
     * 
     * @param operation The UML operation to check for abstractness.
     * @return The abstract symbol ({@code "{abstract} "}) if the operation is abstract, an empty string otherwise.
     */
    private static String getAbstractSymbol(org.eclipse.uml2.uml.Operation operation) {
        if (operation.isAbstract()) {
            return "{abstract} ";
        } else {
            return "";
        }
    }

    /*
     *Generates the parameter string for the given UML operation.
     *
     *@param operation The UML operation to generate the parameter string for. 
     *@return The parameter string in the format "(param1:type1, param2:type2, ...)".
     */
    private static String getParametersString(org.eclipse.uml2.uml.Operation operation) {
        StringBuilder parameters = new StringBuilder();
        parameters.append("(");

        for (Parameter parameter : operation.getOwnedParameters()) {
            if (parameter.getName() == null) {
                break;
            }
            parameters.append(parameter.getName()).append(":").append(parameter.getType().getName()).append(", ");
        }

        if (parameters.length() > 1) {
            parameters.setLength(parameters.length() - 2); // Remove trailing comma and space
        }

        parameters.append(")");

        return parameters.toString();
    }

  
    /*
     * Retrieves the return type of the given UML operation.
     * 
     * @param operation The UML operation to retrieve the return type from.
     * @return The name of the return type, or an empty string if there is no return type defined.
     */
    private static String getReturnType(org.eclipse.uml2.uml.Operation operation) {
        if (operation.getType() != null) {
            return operation.getType().getName();
        } else {
            return "";
        }
    }
}
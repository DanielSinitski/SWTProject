
/*
 * The 'Realization' class contains a static method to convert a UML realization object into a PlantUML realization object.
 */
public class Realization {
    

	/*
	 * Converts a UML realization object into a PlantUML realization object in puml format.
	 * 
	 * @param realization The UML realization object to be converted.
	 * @return The converted PlantUML realization object as a string in puml format.
	 */
    public static String umlRealizationToPumlRealization(org.eclipse.uml2.uml.Realization realization) {
        String ret = "\n";
        String client = realization.getClients().get(0).getName();
        String supplier = realization.getSuppliers().get(0).getName();
        String name = (realization.getName() != null) ? " : " + realization.getName() : "";
        ret += client + " ..|> " + supplier + name + "\n";
        return ret;
    }

}

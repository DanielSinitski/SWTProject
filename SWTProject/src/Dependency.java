/*
 *  The 'Dependency' class contains a static method to convert UML dependency objects into PlantUML dependency objects.

 */
public class Dependency {

	/*
	 *  Converts a UML dependency object into a PlantUML dependency object in puml format.
	 *  
	 *  @param dependency The UML dependency object to be converted.
	 *  @return The converted PlantUML dependency object as a string in puml format.
	 */
    public static String uml_dependency_to_puml_dependency(org.eclipse.uml2.uml.Dependency dependency) {
        String ret = "\n";
        String client = dependency.getClients().get(0).getName();
        String supplier = dependency.getSuppliers().get(0).getName();
        String name = (dependency.getName() != null) ? " : " + dependency.getName() : "";
        ret += client + " ..> " + supplier + name + "\n";
        return ret;
    }
}

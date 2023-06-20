import org.eclipse.uml2.uml.EnumerationLiteral;

<<<<<<< HEAD
/*
 * The 'Enum' class contains a static method to convert UML enumeration objects into PlantUML enumeration objects.
 */
public class Enum {

    
	/*
	 * Converts a UML enumeration object into a PlantUML enumeration object in puml format.
	 * 
	 * @param penum The UML enumeration object to be converted.
	 * @return The converted PlantUML enumeration object as a string in puml format.
	 */
    public static String uml_enum_to_puml_enum(org.eclipse.uml2.uml.Enumeration penum) {
        String ret = "";

        ret += "\nenum " + penum.getName() + "{\n";

        for (EnumerationLiteral lit : penum.getOwnedLiterals()) {
            ret += lit.getName() + "\n";
        }

        ret += "}\n";
        return ret;
    }
=======
public class Enum {
	public static String uml_enum_to_puml_enum(org.eclipse.uml2.uml.Enumeration penum) {
		String ret = "";
		
		ret += "\nenum " + penum.getName() + "{\n";
		
		for (EnumerationLiteral lit : penum.getOwnedLiterals()) {
			ret += lit.getName() + "\n";
		}
		
		ret += "}\n";
		return ret;
	}
>>>>>>> 94fb83d238e2dcb3e83055cf17b2733eb57129dc
}

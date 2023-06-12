import org.eclipse.uml2.uml.EnumerationLiteral;

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
}

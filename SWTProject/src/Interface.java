
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.VisibilityKind;


/*
 * The 'Interface' class contains a static method to convert UML interface objects into PlantUML interface objects.
 */
public class Interface {

	/*
	 * Converts a UML interface object into a PlantUML interface object in puml format.
	 * 
	 * @param inter The UML interface object to be converted.
	 * @return The converted PlantUML interface object as a string in puml format.
	 */
    public static String umlInterfaceToPumlInterface(org.eclipse.uml2.uml.Interface inter) {
        StringBuilder ret = new StringBuilder();
        ret.append("\n");

        // Add class stereotype if present
        Stereotype stereotype = inter.getAppliedStereotype("<<stereotypeName>>");
        if (stereotype != null) {
            ret.append("  ").append("<<" + stereotype.getName() + ">>").append("\n");
        }

        // Add visibility symbol based on class visibility
        VisibilityKind visibility = inter.getVisibility();
        String visibilitySymbol = "";

        if (visibility.toString().equals("public")) {
            visibilitySymbol = "+";
        } else if (visibility.toString().equals("private")) {
            visibilitySymbol = "-";
        } else if (visibility.toString().equals("protected")) {
            visibilitySymbol = "#";
        }

        // Check if class is abstract and add notation
        if (inter.isAbstract()) {
            visibilitySymbol += "{abstract} ";
        }

        ret.append(visibilitySymbol).append("Interface ").append(inter.getName()).append(" {\n");

        for (Property property : inter.getAllAttributes()) {
            visibility = property.getVisibility();
            visibilitySymbol = Class.getVisibilitySymbol(visibility);

            if (property.isStatic()) {
                ret.append("{static} ");
            }

            ret.append("  ").append(visibilitySymbol).append(property.getName()).append(":")
                    .append(property.getType().getName()).append("\n");
        }

        for (Element element : inter.getOwnedElements()) {
            if (element instanceof org.eclipse.uml2.uml.Operation) {
                org.eclipse.uml2.uml.Operation operation = (org.eclipse.uml2.uml.Operation) element;
                ret.append("  ").append(Operation.umlOperationToPumlOperation(operation)).append("\n");
            }
        }

        ret.append("}\n");

        return ret.toString();
    }
}

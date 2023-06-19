import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.VisibilityKind;

public class Class {
	public static String umlClassToPumlClass(org.eclipse.uml2.uml.Class clazz) {
	     StringBuilder ret = new StringBuilder();
	     ret.append("\n");
	     // Add class stereotype if present
	     Stereotype stereotype = clazz.getAppliedStereotype("<<stereotypeName>>");
	     if (stereotype != null) {
	         ret.append("  ").append("<<" + stereotype.getName() + ">>").append("\n");
	     }

	     // Add visibility symbol based on class visibility
	     VisibilityKind visibility = clazz.getVisibility();
	     String visibilitySymbol = "";

	     if (visibility.toString().equals("public")) {
	         visibilitySymbol = "+";
	     } else if (visibility.toString().equals("private")) {
	         visibilitySymbol = "-";
	     } else if (visibility.toString().equals("protected")) {
	         visibilitySymbol = "#";
	     }

	     // Check if class is abstract and add notation
	     if (clazz.isAbstract()) {
	         visibilitySymbol += "abstract ";
	     }

	   
	     ret.append(visibilitySymbol).append("class ").append(clazz.getName()).append(" {\n");

	     for (Property property : clazz.getAllAttributes()) {
	    	 if (property.getAssociation() == null) {
	    		 visibility = property.getVisibility();
	    		 visibilitySymbol = getVisibilitySymbol(visibility);

	    		 if (property.isStatic()) {
	    			 ret.append("{static} ");
	    		 }

	         

	    		 ret.append("  ").append(visibilitySymbol).append(property.getName()).append(":")
	                 .append(property.getType().getName()).append("\n");
	    	 }
	     }

	     for (Element element : clazz.getOwnedElements()) {
	         if (element instanceof org.eclipse.uml2.uml.Operation) {
	             org.eclipse.uml2.uml.Operation operation = (org.eclipse.uml2.uml.Operation) element;
	             ret.append("  ").append(Methode.umlMethodeToPumlMethode(operation)).append("\n");
	         }
	     }
	     
	     

	     ret.append("}\n");
	     
	     if (!clazz.getGeneralizations().isEmpty()) {
	    	 ret.append(clazz.getGeneralizations().get(0).getGeneral().getName() + " <|-- " + clazz.getName());
	     }
	     
	     return ret.toString();
	 }
	public static String getVisibilitySymbol(VisibilityKind visibility) {
	     if (visibility == VisibilityKind.PUBLIC_LITERAL) {
	         return "+";
	     } else if (visibility == VisibilityKind.PRIVATE_LITERAL) {
	         return "-";
	     } else if (visibility == VisibilityKind.PROTECTED_LITERAL) {
	         return "#";
	     } else {
	         return "";
	     }
	 }
}

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.*;

@SuppressWarnings("restriction")
public class start {

	public static void main(String[] args) {
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		UMLResourcesUtil.init(resourceSet);
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
	 	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("uml", new UMLResourceFactoryImpl());

	 	//URI uri = URI.createURI("file:/C:\\Users\\\\d_sin\\eclipse-workspace\\Block4a\\\\model\\Block4.uml");
	 	URI uri = URI.createURI("file:/C:\\Users\\d_sin\\git\\SWTProject\\SWTProject\\model\\Test_papyrus.uml");
	 	//URI uri = URI.createURI("file:/C:\\Users\\d_sin\\eclipse-workspace\\test2\\test2.uml");
	 	
	 	Resource resource = resourceSet.getResource(uri, true);
   	    EcoreUtil.resolveAll(resourceSet);
   	 
   	 Model umlModel = (Model) resource.getContents().get(0);

     for (Element element : umlModel.getOwnedElements()) {
         System.out.println(element);
     }

     umlToPuml(umlModel);

     String puml = "@startuml\r\n" + "enum Sterne {\r\n" + "1\r\n" + "2\r\n" + "3\r\n" + "4\r\n" + "5\r\n" + "}\r\n"
             + "...";

     pumlToUml(puml, resource);
 }

 private static void umlToPuml(org.eclipse.uml2.uml.Model model) {
     System.out.println("\nAnalyze Model:");
     System.out.println(model.getName());

     System.out.println("\n@startuml");

     for (PackageableElement packageableElement : model.getPackagedElements()) {
         if (packageableElement instanceof org.eclipse.uml2.uml.Class ) {
             org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) packageableElement;
             System.out.println(Association.uml_association_to_puml_association(clazz));
             boolean hasAssociations = false;
            
             for (Element element : clazz.getOwnedElements()) {
                 if (element instanceof org.eclipse.uml2.uml.Property) {
                     org.eclipse.uml2.uml.Property property = (org.eclipse.uml2.uml.Property) element;
                     if (property.getAssociation() != null) {
                         hasAssociations = true;
                         break;
                     }
                 }
             }

             if (hasAssociations) {
                 System.out.println(umlClassToPumlClass(clazz));

                 // Add class notes if present
                 for (org.eclipse.uml2.uml.Comment comment : clazz.getOwnedComments()) {
                     System.out.println("note \"" + comment.getBody() + "\" as " + clazz.getName());
                 }
             }
         }   else if(packageableElement instanceof org.eclipse.uml2.uml.Interface) {
             org.eclipse.uml2.uml.Interface inter = (org.eclipse.uml2.uml.Interface) packageableElement;
           System.out.print(uml_interface_to_puml_interface(inter));
           boolean hasAssociations = false;
           
           for (Element element : inter.getOwnedElements()) {
               if (element instanceof org.eclipse.uml2.uml.Property) {
                   org.eclipse.uml2.uml.Property property = (org.eclipse.uml2.uml.Property) element;
                   if (property.getAssociation() != null) {
                       hasAssociations = true;
                       break;
                   }
               }
           }

           if (hasAssociations) {
               System.out.println(uml_interface_to_puml_interface(inter));

               // Add class notes if present
               for (org.eclipse.uml2.uml.Comment comment : inter.getOwnedComments()) {
                   System.out.println("note \"" + comment.getBody() + "\" as " + inter.getName());
               }
           }
         }     else if(packageableElement instanceof org.eclipse.uml2.uml.Enumeration) {
             org.eclipse.uml2.uml.Enumeration enum1 = (org.eclipse.uml2.uml.Enumeration) packageableElement;
          
             System.out.print(uml_enum_to_puml_enum(enum1));
         	
         }
     }

     System.out.println("@enduml");
 }

 private static String umlClassToPumlClass(org.eclipse.uml2.uml.Class clazz) {
     StringBuilder ret = new StringBuilder();

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
         visibilitySymbol += "{abstract} ";
     }

   
     ret.append(visibilitySymbol).append("class ").append(clazz.getName()).append(" {\n");

     for (Property property : clazz.getAllAttributes()) {
         visibility = property.getVisibility();
         visibilitySymbol = getVisibilitySymbol(visibility);

         if (property.isStatic()) {
             ret.append("{static} ");
         }

         

         ret.append("  ").append(visibilitySymbol).append(property.getName()).append(":")
                 .append(property.getType().getName()).append("\n");
     }

     for (Element element : clazz.getOwnedElements()) {
         if (element instanceof org.eclipse.uml2.uml.Operation) {
             org.eclipse.uml2.uml.Operation operation = (org.eclipse.uml2.uml.Operation) element;
             ret.append("  ").append(umlMethodeToPumlMethode(operation)).append("\n");
         }
     }

     ret.append("}\n");
     
     return ret.toString();
 }
private static String uml_interface_to_puml_interface(org.eclipse.uml2.uml.Interface inter) {
	  StringBuilder ret = new StringBuilder();

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
       visibilitySymbol = getVisibilitySymbol(visibility);

       if (property.isStatic()) {
           ret.append("{static} ");
       }

       

       ret.append("  ").append(visibilitySymbol).append(property.getName()).append(":")
               .append(property.getType().getName()).append("\n");
   }

   for (Element element : inter.getOwnedElements()) {
       if (element instanceof org.eclipse.uml2.uml.Operation) {
           org.eclipse.uml2.uml.Operation operation = (org.eclipse.uml2.uml.Operation) element;
           ret.append("  ").append(umlMethodeToPumlMethode(operation)).append("\n");
       }
   }

   ret.append("}\n");

   return ret.toString();
}
private static String uml_enum_to_puml_enum(org.eclipse.uml2.uml.Enumeration penum) {
		String ret = "";
		
		ret += "\nenum " + penum.getName() + "{\n";
		
		for (EnumerationLiteral lit : penum.getOwnedLiterals()) {
			ret += lit.getName() + "\n";
		}
		
		ret += "}\n";
		return ret;
	}

 private static String umlMethodeToPumlMethode(org.eclipse.uml2.uml.Operation operation) {
     StringBuilder ret = new StringBuilder();

     if (operation.isStatic()) {
         ret.append("{static} ");
     }

     ret.append(getAbstractSymbol(operation)).append(getVisibilitySymbol(operation.getVisibility()))
             .append(operation.getName()).append(getParametersString(operation));
     ret.append(": ").append(getReturnType(operation)).append(" {");

     ret.append("}");

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
         VisibilityKind visibility = parameter.getVisibility();
         String visibilitySymbol = getVisibilitySymbol(visibility);

         parameters.append(visibilitySymbol).append(parameter.getName()).append(":")
                 .append(parameter.getType().getName()).append(", ");
     }

     if (parameters.length() > 1) {
         parameters.setLength(parameters.length() - 2); // Remove trailing comma and space
     }

     parameters.append(")");

     return parameters.toString();
 }

 private static String getVisibilitySymbol(VisibilityKind visibility) {
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

 private static String getReturnType(org.eclipse.uml2.uml.Operation operation) {
     if (operation.getType() != null) {
         return operation.getType().getName();
     } else {
         return "";
     }
 }

 private static void pumlToUml(String puml, Resource resource) {
     // TODO: Implement the conversion logic from PlantUML to UML
 }
}
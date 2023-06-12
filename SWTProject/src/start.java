import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

@SuppressWarnings("restriction")
public class start {

	public static void main(String[] args) {
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		UMLResourcesUtil.init(resourceSet);
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
	 	resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("uml", new UMLResourceFactoryImpl());

	 	//URI uri = URI.createURI("file:/C:\\Users\\\\d_sin\\eclipse-workspace\\Block4a\\\\model\\Block4.uml");
	 	//URI uri = URI.createURI("file:/C:\\Users\\d_sin\\git\\SWTProject\\SWTProject\\model\\Test_papyrus.uml");
	 	//URI uri = URI.createURI("file:/C:\\Users\\d_sin\\eclipse-workspace\\test2\\test2.uml");
	 	URI uri = URI.createURI("file:/C:\\Users\\d_sin\\eclipse-workspace\\final_test\\final_test.uml");
	 	
	 	Resource resource = resourceSet.getResource(uri, true);
   	    EcoreUtil.resolveAll(resourceSet);
   	 
   	 Model umlModel = (Model) resource.getContents().get(0);

     for (Element element : umlModel.getOwnedElements()) {
         System.out.println(element);
     }

     umlToPuml(umlModel);
 }

 private static void umlToPuml(org.eclipse.uml2.uml.Model model) {
     System.out.println("\nAnalyze Model:");
     System.out.println(model.getName());

     System.out.println("\n@startuml");

     for (PackageableElement packageableElement : model.getPackagedElements()) {
         if (packageableElement instanceof org.eclipse.uml2.uml.Class ) {
             org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) packageableElement;
             System.out.println(Association.uml_association_to_puml_association(clazz));
            
             for (Element element : clazz.getOwnedElements()) {
                 if (element instanceof org.eclipse.uml2.uml.Property) {
                     org.eclipse.uml2.uml.Property property = (org.eclipse.uml2.uml.Property) element;
                 }
             }

             
             System.out.println(Class.umlClassToPumlClass(clazz));

             // Add class notes if present
             for (org.eclipse.uml2.uml.Comment comment : clazz.getOwnedComments()) {
                 System.out.println("note \"" + comment.getBody() + "\" as " + clazz.getName());
             }
             
         }   else if(packageableElement instanceof org.eclipse.uml2.uml.Interface) {
             org.eclipse.uml2.uml.Interface inter = (org.eclipse.uml2.uml.Interface) packageableElement;
           System.out.print(Interface.uml_interface_to_puml_interface(inter));
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
               System.out.println(Interface.uml_interface_to_puml_interface(inter));

               // Add class notes if present
               for (org.eclipse.uml2.uml.Comment comment : inter.getOwnedComments()) {
                   System.out.println("note \"" + comment.getBody() + "\" as " + inter.getName());
               }
           }
         }     else if(packageableElement instanceof org.eclipse.uml2.uml.Enumeration) {
             org.eclipse.uml2.uml.Enumeration enum1 = (org.eclipse.uml2.uml.Enumeration) packageableElement;
          
             System.out.print(Enum.uml_enum_to_puml_enum(enum1));
         	
         }
     }

     System.out.println("@enduml");
 }
}
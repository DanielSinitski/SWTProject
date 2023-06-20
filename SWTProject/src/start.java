import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
 /*
  * The 'start' class is the entry point of the program for analyzing a UML model and converting it to PlantUML syntax.

  */
@SuppressWarnings("restriction")
public class start {
	
	
	
	/*
	 * The main method of the program.
	 * @param args Command-line arguments.
	 */
    public static void main(String[] args) {
        // Create a resource set
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        UMLResourcesUtil.init(resourceSet);

        // Register resource factories
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("uml", new UMLResourceFactoryImpl());


        // Define the URI of the UML model file
        URI uri = URI.createURI("model\\final_test.uml");

        // Load the UML model from the resource set
        Resource resource = resourceSet.getResource(uri, true);
        EcoreUtil.resolveAll(resourceSet);
        Model umlModel = (Model) resource.getContents().get(0);

        // Analyze and convert the UML model to PlantUML
        umlToPuml(umlModel);
    }


    /*
     *Analyzes and converts the UML model to PlantUML syntax.
     *
     * @param model The UML model to be converted.
     */
    private static void umlToPuml(org.eclipse.uml2.uml.Model model) {
        System.out.println("\nAnalyze Model:");
        System.out.println(model.getName());

        System.out.println("\n@startuml");

        for (PackageableElement packageableElement : model.getPackagedElements()) {
            if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) packageableElement;
                System.out.println(Class.umlClassToPumlClass(clazz));

                // Add class notes if present
                for (org.eclipse.uml2.uml.Comment comment : clazz.getOwnedComments()) {
                    System.out.println("note \"" + comment.getBody() + "\" as " + clazz.getName());
                }

            } else if (packageableElement instanceof org.eclipse.uml2.uml.Interface) {
                org.eclipse.uml2.uml.Interface inter = (org.eclipse.uml2.uml.Interface) packageableElement;
                System.out.print(Interface.uml_interface_to_puml_interface(inter));

                // Add class notes if present
                for (org.eclipse.uml2.uml.Comment comment : inter.getOwnedComments()) {
                    System.out.println("note \"" + comment.getBody() + "\" as " + inter.getName());
                }
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Enumeration) {
                org.eclipse.uml2.uml.Enumeration enum1 = (org.eclipse.uml2.uml.Enumeration) packageableElement;
                System.out.print(Enum.uml_enum_to_puml_enum(enum1));

            } else if (packageableElement instanceof org.eclipse.uml2.uml.Realization) {
                org.eclipse.uml2.uml.Realization real = (org.eclipse.uml2.uml.Realization) packageableElement;
                System.out.print(Realization.uml_realization_to_puml_realization(real));
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Dependency) {
                org.eclipse.uml2.uml.Dependency dep = (org.eclipse.uml2.uml.Dependency) packageableElement;
                System.out.print(Dependency.uml_dependency_to_puml_dependency(dep));
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Association) {
                org.eclipse.uml2.uml.Association association = (org.eclipse.uml2.uml.Association) packageableElement;
                System.out.println(Association.uml_association_to_puml_association(association));
            } else {
                System.err.println("Element " + packageableElement + " konnte nicht übersetzt werden!!!");
            }
        }

        System.out.println("@enduml");
    }
}

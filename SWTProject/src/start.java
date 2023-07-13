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
import java.util.ArrayList;
import java.util.List;
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
    	
    	String path = "";
    	
    	if (args.length != 1) {
    		System.err.println("Error: Bitte Dateipfad angeben oder -h für Hilfe");
    	} else if (args[0].equals("-h")) {
    		System.out.println("Bei Programmaufruf bitte eine .uml Datei angeben\nBsp.: java -jar umlConverter.jar <path>");
    		System.exit(0);
    	} else {
    		path = args[0];
    	}
        // Create a resource set
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        UMLResourcesUtil.init(resourceSet);

        // Register resource factories
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("uml", new UMLResourceFactoryImpl());


        try {
	        URI uri = URI.createURI(path);
	        Resource resource = resourceSet.getResource(uri, true);
	        EcoreUtil.resolveAll(resourceSet);
	        Model umlModel = (Model) resource.getContents().get(0);

	        // Analyze and convert the UML model to PlantUML
	        umlToPuml(umlModel);
        } catch (Exception e) {
        	System.err.print("Error: Datei nicht gefunden an Stelle: " + path);
        	System.exit(1);
        }

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

        List<String> error_messages = new ArrayList<String>();
        
        for (PackageableElement packageableElement : model.getPackagedElements()) {
            if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) packageableElement;
                System.out.println(Class.umlClassToPumlClass(clazz));

            } else if (packageableElement instanceof org.eclipse.uml2.uml.Interface) {
                org.eclipse.uml2.uml.Interface inter = (org.eclipse.uml2.uml.Interface) packageableElement;
                System.out.print(Interface.umlInterfaceToPumlInterface(inter));
                
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Enumeration) {
                org.eclipse.uml2.uml.Enumeration enum1 = (org.eclipse.uml2.uml.Enumeration) packageableElement;
                System.out.print(Enum.umlEnumToPumlEnum(enum1));
                
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Realization) {
                org.eclipse.uml2.uml.Realization real = (org.eclipse.uml2.uml.Realization) packageableElement;
                System.out.print(Realization.umlRealizationToPumlRealization(real));
                
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Dependency) {
                org.eclipse.uml2.uml.Dependency dep = (org.eclipse.uml2.uml.Dependency) packageableElement;
                System.out.print(Dependency.umlDependencyToPumlDependency(dep));
                
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Association) {
                org.eclipse.uml2.uml.Association association = (org.eclipse.uml2.uml.Association) packageableElement;
                System.out.println(Association.umlAssociationToPumlAssociation(association));
                
            } else {
                error_messages.add("Element " + packageableElement + " konnte nicht übersetzt werden!!!");
            }
        }

        System.out.println("@enduml");
        
        System.out.println("\n");
        System.out.println("\n");
    	for (String error_message : error_messages){
    		System.out.println(error_message);
    	}
    }
}

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
   	 
   	    Model uml_model = (Model) resource.getContents().get(0);
   	 	   
		for (Element element : uml_model.getOwnedElements()) {
   	 		System.out.println(element);
   	 	}
		
		uml_to_puml(uml_model);
   	 	
   	 	puml_to_uml("@startuml\r\n"
   	 			+ "enum Sterne {\r\n"
   	 			+ "1\r\n"
   	 			+ "2\r\n"
   	 			+ "3\r\n"
   	 			+ "4\r\n"
   	 			+ "5\r\n"
   	 			+ "}\r\n"
   	 			+ " ... ", resource);
   	 	
	}

	private static void uml_to_puml(org.eclipse.uml2.uml.Model model) {
		
		System.out.println("\nAnalyze Model:");
		System.out.println(model.getName());
		
		System.out.println("\n@startuml");
		
		
		for (PackageableElement packageableElement : model.getPackagedElements()) {

			if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
				org.eclipse.uml2.uml.Class clazz =  (org.eclipse.uml2.uml.Class) packageableElement;
				System.out.println(uml_class_to_puml_class(clazz));
				System.out.println(Association.uml_association_to_puml_association(clazz));
			}
				
		}
		
		System.out.println("\n@enduml");
		
	}
	
	private static String uml_class_to_puml_class(org.eclipse.uml2.uml.Class clazz) {
		
		String ret = "";
		
		ret = ret + "\nclass " + clazz.getName() + " {\n";
		
		for (Property property : clazz.getAllAttributes()) {
			
			if (property.getAssociation() == null) {
				VisibilityKind vk = property.getVisibility();
			
				if (vk.equals(VisibilityKind.PUBLIC)) {
					ret = ret + "+ ";
				}
				if (vk.equals(VisibilityKind.PRIVATE)) {
					ret = ret + "- ";
				}
			
				ret = ret + property.getName() + ":";
			
				ret = ret + property.getType().getName() + "\n";
			}
		}
		
		ret = ret + "}";
				
		return ret;
	}
	

	
	private static void puml_to_uml(String puml, Resource resource) {
		
		/*
		UMLPackage.eINSTANCE.eClass();
		UMLFactory factory = UMLFactory.eINSTANCE;
		
		StateMachine statemachine = factory.createStateMachine();
		statemachine.setName("MyGeneratedStateMachine");
		
		Region region = factory.createRegion();
		region.setName("MyRegion");
		
		statemachine.getRegions().add(region);
		
		Pseudostate ps =  factory.createPseudostate();
		ps.setName("Initial1");
		
		region.getSubvertices().add(ps);
		
		State state = factory.createState();
		state.setName("On");
		
		region.getSubvertices().add(state);
		
		Transition transition = factory.createTransition();
		transition.setSource(ps);
		transition.setTarget(state);
		
		region.getTransitions().add(transition);
		
		NamedElement namedElement = uml_model.getOwnedMember("CoffeeMachineController");
		Operation operation_trigger = ((org.eclipse.uml2.uml.internal.impl.ClassImpl) namedElement).getOperation("switch_on_off", null, null);
				
		Operation operation_effect = ((org.eclipse.uml2.uml.internal.impl.ClassImpl) namedElement).getOperation("set_on", null, null);
		
		CallEvent call_event = factory.createCallEvent();
		call_event.setOperation(operation_trigger);
		
		Trigger trigger = factory.createTrigger();
		trigger.setEvent(call_event);
		
		Behavior behavior = factory.createOpaqueBehavior();
		behavior.setSpecification(operation_effect);
		
		
		transition.getTriggers().add(trigger);
		transition.setEffect(behavior);
		
		uml_model.getPackagedElements().add(call_event);
		
		uml_model.getPackagedElements().add(statemachine);
				
	    try {
	           resource.save(Collections.EMPTY_MAP);
	    } catch (IOException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	    }
		*/
	}

}

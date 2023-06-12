
public class Dependency {
	public static String uml_dependency_to_puml_dependency(org.eclipse.uml2.uml.Dependency dependency) {
		String ret = "\n";
		String client = dependency.getClients().get(0).getName();
        String supplier = dependency.getSuppliers().get(0).getName();
        String name = (dependency.getName()!=null) ? " : " + dependency.getName(): "";
        ret += client + " ..> " + supplier + name + "\n";
		return ret;
	}

}

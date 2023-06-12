import org.eclipse.uml2.uml.NamedElement;

public class Realization {
	public static String uml_realization_to_puml_realization(org.eclipse.uml2.uml.Realization realization) {
		String ret = "\n";
		String client = realization.getClients().get(0).getName();
        String supplier = realization.getSuppliers().get(0).getName();
        String name = (realization.getName()!=null) ? " : " + realization.getName(): "";
        ret += client + " ..|> " + supplier + name + "\n";
		return ret;
	}
}

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ValueSpecification;

public class Association {
	
	
	
	public static String uml_association_to_puml_association(org.eclipse.uml2.uml.Association association) {
		String ret = "";
		Property sourceEnd = association.getMemberEnds().get(0);
		Property targetEnd = association.getMemberEnds().get(1);
		String sourceEndMultiplicityString = getMultiplicityString(sourceEnd);
		String targetEndMultiplicityString = getMultiplicityString(targetEnd);
		String relationType = getRelationTypeString(sourceEnd, targetEnd);
		String associationLabel = (association.getName()!=null)? " : " + association.getName() : "";
		String sourceEndLabelName = (sourceEnd.getName()!=null)? "    " +  sourceEnd.getName() : "";
		String targetEndLabelName = (targetEnd.getName()!=null)? "    " +  targetEnd.getName() : "";
		String sourcemultiplicityWithLabel = getMultiplicityWithLabel(sourceEndMultiplicityString, sourceEndLabelName);
		String targetmultiplicityWithLabel = getMultiplicityWithLabel(targetEndMultiplicityString, targetEndLabelName);
		ret = ret + "\n" + sourceEnd.getType().getName() + sourcemultiplicityWithLabel + relationType + targetmultiplicityWithLabel + targetEnd.getType().getName() + associationLabel + "\n";
		return ret;
	}
	
	public static String getAssociationLabel(Property property) {
		if (property.getAssociation().getName() != null) {
            return " : " + property.getAssociation().getName();
        } else {
            return "";
        }
	}
	
	public static String getRelationTypeString(Property sourceEnd, Property targetEnd) {
		if (isComposition(targetEnd)) {
        	if (targetEnd.isNavigable()) {
        		return "*-->";
        	} else {
        		return "*--";
        	}
        } else if (isAggregation(sourceEnd)) {
        	if (sourceEnd.isNavigable()) {
        		return "<--o";
        	} else return "--o";
        } else if (isAggregation(targetEnd)) {
        	if (targetEnd.isNavigable()){
        		return "o-->";
        	} else return "o--";
        } else if (isComposition(sourceEnd)) {
        	if (sourceEnd.isNavigable()) {
        		return "<--*";
        	} else {
        		return "--*";
        	}
        } else {
        	if (targetEnd.isNavigable()) {
        		return "-->";
        	} else if (sourceEnd.isNavigable()){
        		return "<--";
        	} else return "--";
        }
	}

	public static String getMultiplicityString(Property memberEnd) {
		MultiplicityElement multiplicityElement = (MultiplicityElement) memberEnd;
		ValueSpecification lowerValue = multiplicityElement.getLowerValue();
		ValueSpecification upperValue = multiplicityElement.getUpperValue();

		String lower = (lowerValue != null) ? lowerValue.stringValue() : Integer.toString(multiplicityElement.getLower());
		String upper = (upperValue != null) ? upperValue.stringValue() : Integer.toString(multiplicityElement.getUpper());
		String result = (!upper.equals(lower)) ? " \"" + lower + ".." + upper + "\"" : " \"" + lower + "\"";
		return result;
	}
	

    public static boolean isComposition(Property memberEnd) {
        return memberEnd.getAggregation().getValue() == AggregationKind.COMPOSITE;
    }

    public static boolean isAggregation(Property memberEnd) {
        return memberEnd.getAggregation().getValue() == AggregationKind.SHARED;
    }
    
    public static String getMultiplicityWithLabel(String mult, String label) {
    	if (label == null || label.isEmpty()) {
    		return mult;
    	} else {
    		return mult.substring(0, mult.length() - 1) + label + "\" ";
    	}    
    }

}

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ValueSpecification;

public class Association {
	
	
	
	public static String uml_association_to_puml_association(org.eclipse.uml2.uml.Class clazz) {
		String ret = "";
		
		for (Property property : clazz.getAllAttributes()) {
			if (property.getAssociation() != null) {
				Property sourceEnd = property.getAssociation().getMemberEnds().get(0);
				Property targetEnd = property.getAssociation().getMemberEnds().get(1);
				String sourceEndMultiplicityString = getMultiplicityString(sourceEnd);
				String targetEndMultiplicityString = getMultiplicityString(targetEnd);
				String relationType = getRelationTypeString(sourceEnd, targetEnd);
				String associationLabel = getAssociationLabel(property);
				ret = ret + "\n" + clazz.getName() + targetEndMultiplicityString + relationType + sourceEndMultiplicityString + ((Property) property.getAssociation().getMembers().get(1)).getType().getName() + associationLabel + "\n";
			} 
		}
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
		if (isExtension(sourceEnd, targetEnd)) {
            return "<|--";
        } else if (isComposition(sourceEnd, targetEnd)) {
            return "*--";
        } else if (isAggregation(sourceEnd, targetEnd)) {
            return "o--";
        } else {
            return "--";
        }
	}

	public static String getMultiplicityString(Property memberEnd) {
		MultiplicityElement multiplicityElement = (MultiplicityElement) memberEnd;
		ValueSpecification lowerValue = multiplicityElement.getLowerValue();
		ValueSpecification upperValue = multiplicityElement.getUpperValue();

		String lower = (lowerValue != null) ? lowerValue.stringValue() : Integer.toString(multiplicityElement.getLower());
		String upper = (upperValue != null) ? upperValue.stringValue() : Integer.toString(multiplicityElement.getUpper());
		String result = (!upper.equals(lower)) ? " \"" + lower + ".." + upper + "\" " : " \"" + lower + "\" ";
		return result;
	}
	
	
	public static boolean isExtension(Property sourceEnd, Property targetEnd) {
        return sourceEnd.getAggregation().getValue() == AggregationKind.SHARED && targetEnd.getAggregation().getValue() == AggregationKind.NONE;
    }

    public static boolean isComposition(Property sourceEnd, Property targetEnd) {
        return sourceEnd.getAggregation().getValue() == AggregationKind.COMPOSITE && targetEnd.getAggregation().getValue() == AggregationKind.NONE;
    }

    public static boolean isAggregation(Property sourceEnd, Property targetEnd) {
        return sourceEnd.getAggregation().getValue() == AggregationKind.SHARED && targetEnd.getAggregation().getValue() == AggregationKind.SHARED;
    }

}

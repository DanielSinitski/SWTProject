
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ValueSpecification;
/*
 * The 'Association' class contains static methods to convert UML association objects into PlantUML association objects.
 */
public class Association {

 
/*
 * Converts a UML association object into a PlantUML association object in puml format.
 * 
 * @param association The UML association object to be converted.
 * @return The converted PlantUML association object as a string in puml format.
 */
    public static String uml_association_to_puml_association(org.eclipse.uml2.uml.Association association) {
        String ret = "";
        Property sourceEnd = association.getMemberEnds().get(0);
        Property targetEnd = association.getMemberEnds().get(1);
        String sourceEndMultiplicityString = getMultiplicityString(sourceEnd);
        String targetEndMultiplicityString = getMultiplicityString(targetEnd);
        String relationType = getRelationTypeString(sourceEnd, targetEnd);
        String associationLabel = (association.getName() != null) ? " : " + association.getName() : "";
        String sourceEndLabelName = (sourceEnd.getName() != null) ? "    " + sourceEnd.getName() : "";
        String targetEndLabelName = (targetEnd.getName() != null) ? "    " + targetEnd.getName() : "";
        String sourcemultiplicityWithLabel = getMultiplicityWithLabel(sourceEndMultiplicityString, sourceEndLabelName);
        String targetmultiplicityWithLabel = getMultiplicityWithLabel(targetEndMultiplicityString, targetEndLabelName);
        ret = ret + "\n" + sourceEnd.getType().getName() + sourcemultiplicityWithLabel + relationType + targetmultiplicityWithLabel + targetEnd.getType().getName() + associationLabel + "\n";
        return ret;
    }


    /*
     * Returns the label of the association.
     * 
     * @param property The property representing the association.
     * @return The label of the association.
     */
    public static String getAssociationLabel(Property property) {
        if (property.getAssociation().getName() != null) {
            return " : " + property.getAssociation().getName();
        } else {
            return "";
        }
    }

  
    /*
     * Determines the relation type between the source and target ends of the association.
     * 
     * @param sourceEnd The source end of the association.
     * @param targetEnd The target end of the association.
     * @return The relation type as a string.
     */
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
            if (targetEnd.isNavigable()) {
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
            } else if (sourceEnd.isNavigable()) {
                return "<--";
            } else return "--";
        }
    }

   
/*
 * Retrieves the multiplicity string for the given member end.
 * 
 * @param memberEnd The member end of the association.
 * @return The multiplicity string.
 */
    public static String getMultiplicityString(Property memberEnd) {
        MultiplicityElement multiplicityElement = (MultiplicityElement) memberEnd;
        ValueSpecification lowerValue = multiplicityElement.getLowerValue();
        ValueSpecification upperValue = multiplicityElement.getUpperValue();

        String lower = (lowerValue != null) ? lowerValue.stringValue() : Integer.toString(multiplicityElement.getLower());
        String upper = (upperValue != null) ? upperValue.stringValue() : Integer.toString(multiplicityElement.getUpper());
        String result = (!upper.equals(lower)) ? " \"" + lower + ".." + upper + "\"" : " \"" + lower + "\"";
        return result;
    }

   
/*
 * Checks if the member end represents a composition association.
 * 
 * @param memberEnd The member end of the association.
 * @return true if the member end represents a composition association, false otherwise.
 */
    public static boolean isComposition(Property memberEnd) {
        return memberEnd.getAggregation().getValue() == AggregationKind.COMPOSITE;
    }

  
    /*
     * Checks if the member end represents an aggregation association.
     * 
     * @param memberEnd The member end of the association.
     * @return true if the member end represents an aggregation association, false otherwise.
     */
    public static boolean isAggregation(Property memberEnd) {
        return memberEnd.getAggregation().getValue() == AggregationKind.SHARED;
    }

    /*
     * Returns the multiplicity with the label for the given multiplicity string and label.
     * 
     * @param mult  The multiplicity string.
     * @param label The label.
     * @return The multiplicity with the label.
     */
    public static String getMultiplicityWithLabel(String mult, String label) {
        if (label == null || label.isEmpty()) {
            return mult;
        } else {
            return mult.substring(0, mult.length() - 1) + label + "\" ";
        }
    }
}

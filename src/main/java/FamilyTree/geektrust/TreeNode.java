package FamilyTree.geektrust;

public class TreeNode{
	public String spouse;
	public String parent;
	public String name;
	public boolean male;
	
	TreeNode(){
		spouse = null;
		parent = null;
		name = "name";
		male = true;
	}
	
	public void addMember(String spouse, String parent, String name, boolean male) {
		this.spouse = spouse;
		this.parent = parent;
		this.name = name;
		this.male = male;
	}
}
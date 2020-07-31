package FamilyTree.geektrust;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Scanner;

public class Relation {
	
	/* 
	 * Every member is assigned an index or an identity number
	 * Each member in a couple shares the same index
	 * Every member has a rootIndex, i.e, index of her mother/father
	 * Members with index = 0 are the ones that were not originally part of the family tree-
	 * -but were married to one of the original members of the tree
	 */
	
	/*
	 * Assumption - If the input has a new line, it will have proper 3 strings in the next line with valid input values
	 */
	
	Hashtable<String, TreeNode> memberList = new Hashtable<String, TreeNode>();
	
	Relation() {	
		addMember("Queen Anga", null, "King Shan", true);
		addMember("King Shan", null, "Queen Anga", false);
		addMember("Amba", "Queen Anga", "Chit", true);
		addMember("Chit", null, "Amba", false);
		addMember(null, "Queen Anga", "Ish", true);
		addMember("Lika", "Queen Anga", "Vich", true);
		addMember("Vich", null, "Lika", false);
		addMember("Chitra", "Queen Anga", "Aras", true);
		addMember("Aras", null, "Chitra", false);
		addMember("Satya", null, "Vyan", true);
		addMember("Aras", "Queen Anga", "Satya", false);
		addMember("Dritha", null, "Jaya", true);
		addMember("Jaya", "Amba", "Dritha", false);
		addMember(null, "Amba", "Vritha", true);
		addMember(null, "Amba", "Tritha", false);
		addMember(null, "Lika", "Vila", false);
		addMember(null, "Lika", "Chika", false);
		addMember("Jnki", null, "Arit", true);
		addMember("Arit", "Chitra", "Jnki", false);
		addMember(null, "Chitra", "Ahit", true);
		addMember("Satvy", "Satya", "Asva", true);
		addMember("Asva", null, "Satvy", false);
		addMember("Krpi", "Satya", "Vyas", true);
		addMember("Vyas", null, "Krpi", false);
		addMember(null, "Satya", "Atya", false);
		addMember(null, "Dritha", "Yodhan", true);
		addMember(null, "Jnki", "Laki", true);
		addMember(null, "Jnki", "Lavnya", false);
		addMember(null, "Satvy", "Vasa", true);
		addMember(null, "Krpi", "Kriya", true);
		addMember(null, "Krpi", "Krithi", false);
	}	
	
	public void addMember(String spouse, String parent, String name, boolean male) {
		TreeNode tn = new TreeNode();
		tn.addMember(spouse, parent, name, male);
		memberList.put(name, tn);
	}
	
	public void readInput(String path) {

		try {
			FileReader fr = new FileReader(path);
			Scanner readFile = new Scanner(fr);
			while(readFile.hasNext()) {
				String add = readFile.next();
				if(add.equalsIgnoreCase("ADD_CHILD")) {
					String mother = readFile.next();
					TreeNode tn = memberList.get(mother);
					if(tn == null)
						System.out.println("PERSON_NOT_FOUND");
					else if(tn.male)
						System.out.println("CHILD_ADDITION_FAILED");
					else {
						String name = readFile.next();
						boolean male = readFile.next().equalsIgnoreCase("male")? true : false;
						addMember(null, mother, name, male);
						System.out.println("CHILD_ADDITION_SUCCEEDED");
					}	
				}
				else if(add.equalsIgnoreCase("GET_RELATIONSHIP")) {
					findRelation(readFile.next(), readFile.next());
				}
			}
			readFile.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("No input file found");
		}
	}
	
		
	public void findRelation(String person1, String relate) {
		TreeNode tn = memberList.get(person1);
		String relatives = "";
		try {
			if(tn == null)
				System.out.println("PERSON_NOT_FOUND");
			else {
				switch(relate) {
					case "Siblings":
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							if(node.parent!=null && node.parent.equalsIgnoreCase(tn.parent) && node.name != tn.name)
								relatives = node.name + " " + relatives;
						}
						break;
					case "Daughter":
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							if(node.parent!=null && node.parent.equalsIgnoreCase(tn.name) && !node.male)
								relatives = node.name + " " + relatives;
						}
						break;
					case "Son":
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							if(node.parent!=null && node.parent.equalsIgnoreCase(tn.name) && node.male)
								relatives = node.name + " " + relatives;
						}
						break;		
					case "Paternal-Uncle":
							String father = memberList.get(tn.parent).spouse;
							String grandMother = memberList.get(father).parent;
							for(String key: memberList.keySet()) {
								TreeNode node = memberList.get(key);
								if(node.parent!=null && node.parent.equalsIgnoreCase(grandMother) && node.male && node.name != father)
									relatives = node.name + " " + relatives;
							}
							break;		
					case "Maternal-Uncle":
						String mother = tn.parent;
						String grandMother1 = memberList.get(mother).parent;
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							if(node.parent!=null && node.parent.equalsIgnoreCase(grandMother1) && node.male)
								relatives = node.name + " " + relatives;
						}
						break;		
					case "Paternal-Aunt":
						String father1 = memberList.get(tn.parent).spouse;
						String grandMother2 = memberList.get(father1).parent;
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							if(node.parent!=null && node.parent.equalsIgnoreCase(grandMother2) && !node.male)
								relatives = node.name + " " + relatives;
						}
						break;		
					case "Maternal-Aunt":
						String mother1 = tn.parent;
						String grandMother3 = memberList.get(mother1).parent;
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							if(node.parent!=null && node.parent.equalsIgnoreCase(grandMother3) && node.male && node.name != mother1)
								relatives = node.name + " " + relatives;
						}
						break;		
					case "Sister-In-Law":
						String spouse = tn.spouse;
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							String spouseParent = spouse!=null? memberList.get(spouse).parent: "";
							if(node.parent!=null && node.parent.equalsIgnoreCase(spouseParent) 
									&& !node.male && node.name != spouse)
								relatives = node.name + " " + relatives;
							if(node.parent!=null && node.parent.equalsIgnoreCase(tn.parent) && node.spouse != null && node.male && node.name != tn.name)
								relatives = node.spouse + " " + relatives;
						}
						break;	
					case "Brother-In-Law":
						String spouse1 = tn.spouse;
						for(String key: memberList.keySet()) {
							TreeNode node = memberList.get(key);
							String spouseParent1 = spouse1!=null? memberList.get(spouse1).parent: "";
							if(node.parent!=null && node.parent.equalsIgnoreCase(spouseParent1) 
									&& node.male && node.name != spouse1)
								relatives = node.name + " " + relatives;
							if(node.parent!=null && node.parent.equalsIgnoreCase(tn.parent) && node.spouse != null && !node.male && node.name != tn.name)
								relatives = node.spouse + " " + relatives;
						}
						break;	
				}			
				if(relatives.length()>0)
					System.out.println(relatives);
				else
					System.out.println("NONE");
			}
		}
		catch(Exception e) {
			System.out.println("NONE");
		}
	}
	
	public static void main(String args[]) {
		String path = args[0];
		Relation ft = new Relation();
		ft.readInput(path);
	}
}

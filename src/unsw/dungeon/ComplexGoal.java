package unsw.dungeon;

import java.util.ArrayList;

public class ComplexGoal implements Goal {
	private ArrayList<Goal> goals;
	private Completion completion;
	
	public ComplexGoal(Completion completion) {
		this.goals = new ArrayList<>();
		this.completion = completion;
	}
	
	public ArrayList<Goal> getGoals() {
		return this.goals;
	}
	
	public void addGoal(Goal g) {
		if (!this.goals.contains(g)) {
			this.goals.add(g);
		}
	}
	
	public void removeGoal(Goal g) {
		if (this.goals.contains(g)) {
			this.goals.remove(g);
		}
	}

	@Override
	public boolean isComplete() {
		return completion.isComplete(goals);
	}
	
	public Completion getCompletion() {
		return completion;
	}
}


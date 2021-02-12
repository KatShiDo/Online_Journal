package com.katshido.online_journal;

import java.util.*;
import java.io.*;

public class Class {

	public String Number;
	public Learner[] Learners;

	public Class(String Number)
	{
		this.Number = Number;
	}

	public String getList()
	{
		StringBuilder result = new StringBuilder();
		result.append("Класс: ").append(this.Number).append("\n");
		for (Learner learner: this.Learners)
		{
			result.append(learner.FullName).append(", ").append(learner.Age).append("\n");
		}
		return result.toString();
	}

	public String getListParrents()
	{
		StringBuilder result = new StringBuilder();
		result.append("Класс: ").append(this.Number).append("\n");
		for (Learner learner: this.Learners)
		{
			result.append(learner.FullName).append(", ").append("\n");
			for (Parent parent: learner.Parents)
			{
				result.append("\t").append(parent.FullName).append(", ").append(parent.Phone).append("\n");
			}
		}
		return result.toString();
	}

}

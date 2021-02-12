package com.katshido.online_journal;

import android.content.res.Resources;

import java.util.*;
import java.io.*;

public class School {

	public Employee[] Employees;
	public Teacher[] Teachers;
	public Learner[] Learners;
	public String Address;
	public String Name;
	public Class[] Classes;

	public String getListTeachers()
	{
		StringBuilder result = new StringBuilder();
		for (Teacher teacher: this.Teachers)
		{
			result.append(teacher.FullName).append(", ").append(teacher.Position).append(", ").append(teacher.Qualification).append("\n\n");
		}
		return result.toString();
	}

	public String getListEmployees()
	{
		StringBuilder result = new StringBuilder();
		for (Employee employee: this.Employees)
		{
			result.append(employee.FullName).append(", ").append(employee.Position).append("\n\n");
		}
		return result.toString();
	}

	public String getListLearners()
	{
		StringBuilder result = new StringBuilder();
		for (Class cl: this.Classes)
		{
			result.append(cl.getList());
		}
		return result.toString();
	}

	public void getOnlineJournal() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public String getParticipants()
	{
		StringBuilder result = new StringBuilder();
		for (Employee employee: this.Employees)
		{
			result.append(employee.FullName).append(", ").append(employee.CardID).append("\n");
		}
		for (Teacher teacher: this.Teachers)
		{
			result.append(teacher.FullName).append(", ").append(teacher.CardID).append("\n");
		}
		for (Learner learner: this.Learners)
		{
			result.append(learner.FullName).append(", ").append(learner.CardID).append("\n");
		}
		return result.toString();
	}

	public String getListLearnersWithParents()
	{
		StringBuilder result = new StringBuilder();
		for (Class cl: this.Classes)
		{
			result.append(cl.getListParrents());
		}
		return result.toString();
	}
}

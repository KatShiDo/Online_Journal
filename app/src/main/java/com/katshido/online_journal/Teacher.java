package com.katshido.online_journal;

import java.util.*;
import java.io.*;

public class Teacher extends Participant{

	public String Position;
	public String Qualification;

	public Teacher(int CardID, String FullName, String Phone, String Position, String Qualification)
	{
		this.CardID = CardID;
		this.FullName = FullName;
		this.Phone = Phone;
		this.Position = Position;
		this.Qualification = Qualification;
	}


}

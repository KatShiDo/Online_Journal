package com.katshido.online_journal;

import java.util.*;
import java.io.*;

public class Employee extends Participant{

	public String Position;

	public Employee(int CardID, String FullName, String Phone, String Position)
	{
		this.CardID = CardID;
		this.FullName = FullName;
		this.Phone = Phone;
		this.Position = Position;
	}

}

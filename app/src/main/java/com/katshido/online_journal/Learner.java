package com.katshido.online_journal;

import java.util.*;
import java.io.*;

public class Learner extends Participant
{
	public int Age;
	public Parent[] Parents;
	public String[] ClassCaptions;

	public Learner(int CardID, String FullName, String Phone, int Age)
	{
		this.CardID = CardID;
		this.FullName = FullName;
		this.Phone = Phone;
		this.Age = Age;
	}

}

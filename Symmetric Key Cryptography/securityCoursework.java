class securityCoursework{

//Store ciphertexts into array
private static final String[] ciphertext = {"1B114D110C09000100154F471E0A0E1F001115410D544F12480752120204000A00150144531A0E1A44441A074E540E544F0C1D55174E03080C1153410C11544E0F03160600061A56085516180003005218",
"1D1C455411190A074F024F0B52120007000A040C09444F0746161713562E170B42111D4F0054071C0B4D553752110A4B4F180B540D0109020E1C53540A1054460F071C17490E06534D010B1A45114F481615050B0000120541060B000B1A15000914492D0F0016",
"1D1C4554250A111200244E04001C1100490B0B413F540E0844030005560C015941541C591E19041A164916504B1116000E19154F170711050445154F105500480F55161A43130A50191C0C06001B09001618040D10521A1E49174F440E0113",
"0C1A4306181B111A4F0F000E0145151C4544151303430A1553421D0756001C1A4F10064E1454004E0945060341130A00061B5253100D0D4D084504411B5515534A011C54480817454D1C171B0017004E07110F1A17",
"1D1C4506044B0401454154101D45150D5001164103464F034E01001806111B164E540E4C141B130710481850530D024D0A010049064E00030A170A50161C1B4E4A141D10000000590018061C521D0C00161A021C1D5001194F1A",
"1D1C4554370202164E045202520608044801174105534F07000F17151E0A16594F124F451D17131714541C1E47540E4C1F1D1342001A0C0E49111658165516594A00001D4E0653414D06061A49111C001C12410A0D461315521101544F36134515131B450D0D03",
"1A00521B0F0C4517490649131309410749030B0018551D03534213131345131700111C53161A1507054C550245051A491D101F450B1A450B06175353071601520F55000D5315164D1E",
"0C02451A411F0D1600124D061E090407544415041E5300080001130F56061A184E130A00071C044E074F000253114F4F09550648004E03181D100145",
"1D1C4554220A0000411300041B1509115244040D1F4F4F0D4E0D050F5604015941541C481A12154E0749051845064F491C551D4E004E0A0B49111B4542061D4D1A1916075441154F1F1810484F124F451D17131714541C1F4E",
"1B2761540818451E410545471D0341004801450802491B0F410E520D1311061C52074F4F1554150601000605521A0E4D0A06524F034E370207452149141007544A34171D00321B41001C1148411A0B003F110E00055211506110034502141C",
"0B184F170A4B061A50094515014515154B0145004C4E1A0B420700411903521B49001C00121A054E014E160259041B001B1D174D450F164D084500490C1218454A001D1D5441124E095513094410064E145415060100051C411D01540A0D0600151D4911060507"
 };

//Longest length of ciphertexts
public static int longestElement(String[]array)
{
	int index = 0; 
	int elementLength = array[0].length();
	for(int i=1; i< array.length; i++) {
		if(array[i].length() > elementLength) 
		{
			index = i; 
			elementLength = array[i].length();//keep variable of max length so far
		}
	}
	return elementLength;
}
//Fill ciphertexts with dashes so they are all same length
public static void fillArray(String[]array)
{
	int maxLength = longestElement(array);
	for(int i=0; i<array.length; i++)
	{
		int currentLength = array[i].length();
		if(currentLength<maxLength)
		{
			for(int j=currentLength; j<maxLength; j+=2)
			{ 
				array[i] = array[i] + "2d";//fills rest of array with spaces

			}
		}
	}
}
					
//Decrypt character by character (stream cipher)
public static char GetCiphertextChar(int ciphertextNumber, int position)
{
	//Convert specific part of string to chars
	String cipher;
	char ciphertextChar;
	cipher = ciphertext[ciphertextNumber].substring(position*2, position*2+2);
	ciphertextChar = (char) Integer.parseInt(cipher,16);
	return ciphertextChar;
}


//At a given position of the key, guess a letter (assume there would be space in that postion in one of messages)
public static char GetKeyLetter(int position)
{
	char[] ciphertextChar = new char[11];
	char keyLetter = 255;
	char[][] ciphertextXor = new char[11][11];
	for (int ciphertextNumber = 0; ciphertextNumber < 11; ciphertextNumber++)
	{
		ciphertextChar[ciphertextNumber] = GetCiphertextChar(ciphertextNumber, position); //Return a character at given position from every string
	}
	for (int i = 0; i < 11; i++)
	{
		for (int j = 0; j < 11; j++)
		{
			if (i != j)
			{

				ciphertextXor[i][j] = (char)( ciphertextChar[i] ^  ciphertextChar[j]); //Then xor these characters with one another
			}
		}
	}
	boolean valid = true;

	for (int i = 0; i < 11; i++) 
	{
		valid = true;
		for (int j = 0; j < 11; j++)  
		{
			if (ciphertextXor[i][j] == 0) //There could be two messages with a space in the same position.
			{
				continue;
			}
			else if (ciphertextXor[i][j] > 96 && ciphertextXor[i][j] < 123) //Range for [a-z] in ASCII.
			{
				continue;
			}
			else if (ciphertextXor[i][j] > 64 && ciphertextXor[i][j] < 91) //Range for [A-Z] in ASCII.
			{
				continue;
			}
			else
			{
				valid = false;
				break;
			}
		}
		if (valid) //If a space in some message convert other messages to [a-zA-Z].
		{		
			keyLetter = (char)(ciphertextChar[i] ^ (int)' '); //We can use that message to get a letter of the key.
		}
	}
	return keyLetter; //Otherwise return 255 as a default.
}

public static void main(String[]args)
{
	String[] messages = new String[11];
	char messageChar;
	char keyLetter;
	fillArray(ciphertext);//All ciphertexts are same length

	System.out.println("Decrypting...");
	
	for (int position = 0; position <(ciphertext[10].length())/2; position++) //Then decrypt letter by letter.
	{
		keyLetter = GetKeyLetter(position); //Get a letter of the key
		for (int ciphertextNumber = 0; ciphertextNumber < 11; ciphertextNumber++)
		{
			if (keyLetter == 255) //If the key was not found
			{				
				messages[ciphertextNumber] = messages[ciphertextNumber] + "_";//Replace that character with a '_'
				continue;
			} 
			//If we found a key
			messageChar = (char)(GetCiphertextChar(ciphertextNumber, position) ^ keyLetter); //decrypt
			messages[ciphertextNumber] = messages[ciphertextNumber] + messageChar; //and reconstruct the original message.
		}
	}
	for (int i = 0; i < 11; i++)
	{
		System.out.println(messages[i]);
	}
}
}

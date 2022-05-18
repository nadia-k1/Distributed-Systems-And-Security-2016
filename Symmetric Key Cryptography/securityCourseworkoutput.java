public class securityCourseworkoutput {
	private final static String[] ciphertexts = {"1B114D110C09000100154F471E0A0E1F001115410D544F12480752120204000A00150144531A0E1A44441A074E540E544F0C1D55174E03080C1153410C11544E0F03160600061A56085516180003005218",
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

	private static int[][] ciphertext = new int[ciphertexts.length][];
	private static char[][] plaintext = new char[ciphertexts.length][];
	private static int minLength;

	public static void main(String[] args) 
	{
		for (int i = 0; i < ciphertexts.length; i++) 
		{
			ciphertext[i] = convertToArray(ciphertexts[i]);//create 2d array that splits ciphertext so we can decrypt character by character
			plaintext[i] = new char[ciphertext[i].length];//initialise plaintext
			for (int k = 0; k < plaintext[i].length; k++)
			{
				plaintext[i][k] = '_';//creating empty plaintext for now
			}
		}

		for (int i = 0; i < ciphertexts.length; i++) 
		{
			for (int j = 0; j < ciphertexts.length; j++) 
			{
				if (j == i)
				{
					continue;
				}
				if(ciphertext[i].length < ciphertext[j].length)
				{
				
					 minLength = ciphertext[i].length;
				}
				else
				{
					 minLength = ciphertext[j].length;
				}
				for (int k = 0; k < minLength; k++) 
				{
					int x = (ciphertext[i][k] ^ ciphertext[j][k]);//xor ciphertexts characters

					if ((x >= 'A' && x <= 'Z') || (x >= 'a' && x <= 'z')) //if xor produced recognisable characters
					{
						if (checkAll(i, ciphertext[i][k], k))
						{
							for (int l = 0; l < plaintext.length; l++) 
							{
								if (k >= plaintext[l].length || plaintext[l][k] != '_')
								{
									continue;
								}
								x = (char) (ciphertext[i][k] ^ ciphertext[l][k] ^ ' ');
								if (l == i || x == 0)
								{
									plaintext[l][k] = ' ';
								}
								else
								{
									plaintext[l][k] = (char) x;
								}
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < plaintext.length; i++) 
		{
			for (int k = 0; k < plaintext[i].length; k++)
			{
				System.out.print(plaintext[i][k]);
			}
			System.out.println();
		}

	}

	private static boolean checkAll(int arrayIndex, int ch, int charIndex) 
	{
		int errorCount = 0;
		for (int j = 0; j < ciphertexts.length; j++) 
		{
			if (j == arrayIndex || ciphertext[j].length <= charIndex)
			{
				continue;
			}
			int x = (ch ^ ciphertext[j][charIndex]);
			if (x == 0)
			{
				continue;
			}
			if (!((x >= 'A' && x <= 'Z') || (x >= 'a' && x <= 'z'))) 
			{
				errorCount++;
				if (errorCount > 2)
				{
					return false;
				}
			}
		}
		return true;
	}

	private static int[] convertToArray(String str) 
	{
		int[] r = new int[str.length() / 2];
		for (int i = 0; i < r.length; i++)
		{
			r[i] = Integer.valueOf(str.substring(i * 2, (i + 1) * 2), 16);//splits str by twos (hexadecimal)
		}
		return r;
	}
}

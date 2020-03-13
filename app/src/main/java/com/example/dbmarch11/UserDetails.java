package com.example.dbmarch11;

public class UserDetails
{
    private String name,address,mobileNo,profession;
    int userId;

//    commented out for safety
//    updated details
    private String gender,corona,ageRange;

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getCorona()
    {
        return corona;
    }

    public void setCorona(String corona)
    {
        this.corona = corona;
    }

    public String getAgeRange()
    {
        return ageRange;
    }

    public void setAgeGroup(String ageRange)
    {
        this.ageRange = ageRange;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    public String getProfession()
    {
        return profession;
    }

    public void setProfession(String profession)
    {
        this.profession = profession;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
}

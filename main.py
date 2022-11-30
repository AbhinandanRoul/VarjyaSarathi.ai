import streamlit as st
import pandas as pd
user_auth_dict= {"user1": "pass", "user2": "pass", "user3":"pass","user4":"pass"}
bid_list = []
user_list=[]
commodity_list=[]
qty_list=[]

st.title("Industrial Portal- Recycling Bidding")
userid=st.text_input("Enter userid")
password=st.text_input("Enter password", type="password")

if userid in user_auth_dict.keys():
    if password == user_auth_dict[userid]:
        st.success("Login Successful")
        commodity=st.selectbox("Select a commodity", ["Plastic", "Paper", "Metals", "Food Packaging", "Bio-Medical Waste", "E-waste"])
        bid_price = st.number_input("Enter bidding price INR/quintal")
        qty=st.number_input("Enter quantity in quintals")
        if st.button("Submit"):
            st.write("Your bid has been submitted successfully")
            # create a dict with userid and bid price
            user_list.append(userid)
            bid_list.append(bid_price)
            commodity_list.append(commodity)
            qty_list.append(qty)

            #st.write(user_list, bid_list, commodity_list)
            df = pd.DataFrame({"Userid": user_list, "Commodity": commodity_list,"Quantity":qty_list, "Bidding Price": bid_list})
            st.write(df)
            df.to_csv("bid.csv", index=False, mode='a',header=False)
    else:
        st.write("Please Login")

st.write("Your BID Status")
df=pd.read_csv("bid.csv")
#find the highest bidder for each commodity
df1=df.groupby("Commodity")["Bidding Price"].max()
#print(df1)
print("------------------------------------------------------")
#extract the userid, commodity and bid price for the highest bidder
df2=df[df["Bidding Price"].isin(df1)]
#print(df2)

for i in range(0,len(df2['Userid'])):
    if df2['Userid'].iloc[i] == userid:
        st.write("You are the highest bidder for", df2['Commodity'].iloc[i])
        continue



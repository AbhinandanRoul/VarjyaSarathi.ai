import streamlit as st
import pandas as pd
user_auth_dict= {"user1": "pass", "user2": "pass", "user3":"pass","user4":"pass"}
bid_list = []
user_list=[]
commodity_list=[]
qty_list=[]

st.title("Industrial Portal Demo")
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



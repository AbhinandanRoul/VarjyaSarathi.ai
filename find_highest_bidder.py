import numpy as np, pandas as pd
import streamlit as st

df=pd.read_csv("bid.csv")
#find the highest bidder for each commodity
df1=df.groupby("Commodity")["Bidding Price"].max()
print(df1)
print("------------------------------------------------------")
#extract the userid, commodity and bid price for the highest bidder
df2=df[df["Bidding Price"].isin(df1)]
print(df2)

for i in range(0,len(df2['Userid'])):
    if df2['Userid'].iloc[i] == userid:
        st.write("You are the highest bidder for", df2['Commodity'].iloc[i])
        continue
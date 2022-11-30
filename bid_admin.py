import streamlit as st, pandas as pd
st.set_page_config(layout="wide")
df=pd.read_csv("bid.csv")
df1=df.groupby("Commodity")["Bidding Price"].max()
print(df1)
#extract the userid, commodity and bid price for the highest bidder
df2=df[df["Bidding Price"].isin(df1)]
st.header("Current Results")
st.table(df2)

st.title("Bid Monitor-Live")
df=pd.read_csv("bid.csv")
st.table(df)




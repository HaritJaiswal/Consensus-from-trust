# Consensus-from-trust
*Cryptocurrency Project*

A distributed consensus algorithm is designed, given a graph of “trust” relationships between nodes. This is an alternative method of resisting sybil attacks and achieving consensus; it has the benefit of not “wasting” electricity like proof-of-work does.
Nodes can be either compliant or malicious. Malicious nodes have the following behaviour:
● be functionally dead and never actually broadcast any transactions.
● constantly broadcasts its own set of transactions and never accept transactions given to it.
● change behavior between rounds to avoid detection.

The graph of nodes can have variable parameters. A robust ​CompliantNode​ class is developed that will work in all combinations of the graph parameters. At the end of each round, the node will see the list of transactions that were broadcast to it. Goal is to make as many nodes reach consensus as possible in limited number of rounds.

Insight: To increase number of nodes reaching consensus, the broadcast in the last round is ignored as majority of nodes have built up a set of consensus transactions which might get disrupted from the latest transactions.

For further details, refer to the pdf file.

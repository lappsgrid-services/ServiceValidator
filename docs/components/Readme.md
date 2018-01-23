# Checking services

This directory contains the result of various checks on a series of services. Much of it is done on several versions of a small [sample file](karen-flies.txt) with minimal content that still contains enough richness so services should actually produce some output. The services used are the ones used at the LAPPS/Galaxy pages at http://galaxy.lappsgrid.org/ and http://jetstream.lappsgrid.org.

The following services were tested or will be tested:

- [Sentence Splitters](splitters/index.md)
- [Tokenizers](tokenizers/index.md)
- [POS Taggers](taggers/index.md)
- [Chunkers](chunkers/index.md)
- Named Entity Recognizers
- Coreference Tools
- Phrase Structure Parsers
- Dependency Structure Parsers

Things to look at

- losing the context
- losing the top-level metadata
- losing layers

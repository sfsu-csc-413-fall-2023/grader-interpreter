TARBALL=tests.tar

jrob-test:
	@echo "Cleaning project workspace..."
	@find . -name "*.class" -type f -delete
	@rm -rf target
	@rm -f sources.txt

	@echo "Compiling..."
	@find . -name "*.java" -not -path "./tests/*" > sources.txt
	@javac -d target @sources.txt
	@cat sources.txt
	@rm sources.txt

package-tests:
	@gtar cf $(TARBALL) tests
	@git add .
	@git commit -m "Test updates (tarball regenerated)"
	@git push
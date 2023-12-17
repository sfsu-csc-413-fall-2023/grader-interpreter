TARBALL=tests.tar

package-tests:
	@gtar cf $(TARBALL) tests
	@git add .
	@git commit -m "Test updates (tarball regenerated)"
	@git push